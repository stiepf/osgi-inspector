/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.stiepf.inspector.components.internal;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.aries.blueprint.PassThroughMetadata;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.osgi.service.blueprint.reflect.BeanMetadata;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.ServiceMetadata;
import org.osgi.service.blueprint.reflect.ServiceReferenceMetadata;
import org.osgi.service.blueprint.reflect.Target;

import com.github.stiepf.inspector.components.ComponentDescription;
import com.github.stiepf.inspector.components.ComponentQuery;
import com.github.stiepf.inspector.components.ComponentType;
import com.github.stiepf.inspector.components.ContainerDescription;

class ComponentQueryImpl implements ComponentQuery {

  private BundleContext bundleContext;

  private List<ComponentPredicate> predicates = new LinkedList<ComponentPredicate>();

  private List<ServiceReferencePredicate> serviceRefPredicates = new LinkedList<ServiceReferencePredicate>();

  public ComponentQueryImpl(BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  @Override
  public ComponentQuery componentId(final String id) {
    return addPredicate(new ComponentPredicate() {
      @Override
      boolean matches(ComponentDescription description) {
        String componentId = description.getId();
        if (componentId != null) {
          return componentId.toLowerCase().contains(id.toLowerCase());
        } else {
          return false;
        }
      }
    });
  }

  @Override
  public ComponentQuery bundleId(final Long bundleId) {
    return addPredicate(new ServiceReferencePredicate() {
      @Override
      boolean matches(ServiceReference reference) {
        return bundleId.equals(reference.getBundle().getBundleId());
      }
    });
  }

  @Override
  public ComponentQuery symbolicName(final String symbolicName) {
    return addPredicate(new ServiceReferencePredicate() {
      @Override
      boolean matches(ServiceReference reference) {
        return reference.getBundle().getSymbolicName().equals(symbolicName);
      }
    });
  }

  @Override
  public ComponentQuery type(final String className) {
    return addPredicate(new ComponentPredicate() {
      @Override
      boolean matches(ComponentDescription description) {
        return description.getClassNames().contains(className);
      }
    });
  }

  @Override
  public ComponentQuery bean() {
    return addPredicate(new ComponentPredicate() {
      @Override
      boolean matches(ComponentDescription description) {
        return ComponentType.BEAN.equals(description.getComponentType());
      }
    });
  }

  @Override
  public ComponentQuery service() {
    return addPredicate(new ComponentPredicate() {
      @Override
      boolean matches(ComponentDescription description) {
        return ComponentType.SERVICE.equals(description.getComponentType());
      }
    });
  }

  @Override
  public ComponentQuery reference() {
    return addPredicate(new ComponentPredicate() {
      @Override
      boolean matches(ComponentDescription description) {
        return ComponentType.REFERENCE.equals(description.getComponentType());
      }
    });
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ContainerDescription> list() {
    List<ContainerDescription> result = new LinkedList<ContainerDescription>();

    try {
      ServiceReference[] references = bundleContext.getAllServiceReferences(BlueprintContainer.class.getName(), null);
      for (ServiceReference r : references) {
        if (serviceMatches(r)) {
          Bundle b = r.getBundle();

          ContainerDescription cd = new ContainerDescription(b);

          BlueprintContainer bc = (BlueprintContainer) bundleContext.getService(r);
          Set<String> componentIds = (Set<String>) bc.getComponentIds();
          for (String cid : componentIds) {
            ComponentMetadata cm = bc.getComponentMetadata(cid);
            ComponentDescription compDesc = convert(bc, cm);
            if (componentMatches(compDesc)) {
              cd.addComponentDescription(compDesc);
            }
          }
          if (!cd.getComponentDescriptions().isEmpty()) {
            result.add(cd);
          }
          bundleContext.ungetService(r);
        }
      }
    } catch (InvalidSyntaxException e) {
      // null filter prevents InvalidSyntaxException
    }

    return result;
  }

  private boolean componentMatches(ComponentDescription description) {
    for (ComponentPredicate bp : predicates) {
      if (!bp.matches(description))
        return false;
    }
    return true;
  }

  private boolean serviceMatches(ServiceReference r) {
    for (ServiceReferencePredicate srp : serviceRefPredicates) {
      if (!srp.matches(r))
        return false;
    }
    return true;
  }

  private ComponentQuery addPredicate(ComponentPredicate predicate) {
    predicates.add(predicate);
    return this;
  }

  private ComponentQuery addPredicate(ServiceReferencePredicate predicate) {
    serviceRefPredicates.add(predicate);
    return this;
  }

  private ComponentDescription convert(BlueprintContainer container, ComponentMetadata metadata) {
    ComponentDescription result = new ComponentDescription(metadata.getId(), ComponentType.getComponentType(metadata));
    switch (result.getComponentType()) {
      case BEAN:
        result.addClassName(getClassOfBeanComponent(container, (BeanMetadata) metadata));
        break;
      case SERVICE:
        for (String className : getClassesOfService(container, (ServiceMetadata) metadata)) {
          result.addClassName(className);
        }
        break;
      case REFERENCE:
        result.addClassName(getClassOfReference(container, (ServiceReferenceMetadata) metadata));
        break;
      case IMPLICIT:
        result.addClassName(getClassOfPassThrough(container, (PassThroughMetadata) metadata));
        break;
      default:
        result.addClassName("unknown");
    }
    return result;
  }

  private String getClassOfBeanComponent(BlueprintContainer container, BeanMetadata metadata) {
    String localType = metadata.getClassName();
    if (localType == null) {
      Target factoryComponent = metadata.getFactoryComponent();
      if (factoryComponent != null) {
        if (factoryComponent instanceof PassThroughMetadata) {
          PassThroughMetadata ptm = (PassThroughMetadata) factoryComponent;
          localType = ptm.getObject().getClass().getName();
        } else {
          localType = factoryComponent.getClass().getName();
        }
      } else {
        localType = getConcreteType(container, metadata);
      }
    }
    return localType;
  }

  @SuppressWarnings("unchecked")
  private List<String> getClassesOfService(BlueprintContainer container, ServiceMetadata metadata) {
    List<String> result = new LinkedList<String>();
    result.addAll(metadata.getInterfaces());
    if (result.isEmpty()) {
      result.add(getConcreteType(container, metadata));
    }
    return result;
  }

  private String getClassOfReference(BlueprintContainer container, ServiceReferenceMetadata metadata) {
    String interfaceType = metadata.getInterface();
    if (interfaceType == null) {
      interfaceType = getConcreteType(container, metadata);
    }
    return interfaceType;
  }

  private String getClassOfPassThrough(BlueprintContainer container, PassThroughMetadata metadata) {
    String type = metadata.getObject().getClass().getName();
    return type;
  }

  private String getConcreteType(BlueprintContainer container, ComponentMetadata metadata) {
    return container.getComponentInstance(metadata.getId()).getClass().getName();
  }
}

// }
// if (metadata instanceof ServiceMetadata) {
// ServiceMetadata sm = (ServiceMetadata) metadata;
// List<String> interfaces = (List<String>) sm.getInterfaces();
// return interfaces.contains(className);
// }
// if (metadata instanceof ServiceReferenceMetadata) {
// ServiceReferenceMetadata srm = (ServiceReferenceMetadata) metadata;
// return className.equals(srm.getInterface());
// }