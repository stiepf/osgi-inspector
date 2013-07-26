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
package com.github.stiepf.inspector.services.internal;

import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.github.stiepf.inspector.services.ServiceQuery;

class ServiceQueryImpl implements ServiceQuery {

  private BundleContext bundleContext;

  private List<ServicePredicate> predicates = new LinkedList<ServicePredicate>();

  private String knownInterface;

  private String filter;

  ServiceQueryImpl(BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  @Override
  public ServiceQuery hasInterface(String serviceInterface) {
    knownInterface = serviceInterface;
    return this;
  }

  @Override
  public ServiceQuery providedBy(Bundle bundle) {
    return addPredicate(new ProvidedByPredicate(bundle));
  }

  @Override
  public ServiceQuery consumedBy(Bundle bundle) {
    return addPredicate(new ConsumedByPredicate(bundle));
  }

  
  @Override
  public ServiceQuery hasProperty(String key) {
    return addPredicate(new HasPropertyPredicate(key));
  }

  @Override
  public ServiceQuery hasPropertyValue(String key, Object value) {
    return addPredicate(new HasPropertyValuePredicate(key, value));
  }

  @Override
  public ServiceQuery matchesFilter(String filter) {
    this.filter = filter;
    return this;
  }

  @Override
  public List<ServiceReference> list() {
    List<ServiceReference> result = new LinkedList<ServiceReference>();

    ServiceReference[] references;
    try {
      references = bundleContext.getAllServiceReferences(knownInterface, filter);
    } catch (InvalidSyntaxException e) {
      references = new ServiceReference[0];
    }

    for (ServiceReference sr : references) {
      if (allPredicatesMatch(sr)) {
        result.add(sr);
      }
    }
    return result;
  }

  private boolean allPredicatesMatch(ServiceReference sr) {
    for (ServicePredicate sp : predicates) {
      if (!sp.matches(sr))
        return false;
    }
    return true;
  }

  private ServiceQuery addPredicate(ServicePredicate predicate) {
    predicates.add(predicate);
    return this;
  }
}
