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
package com.github.stiepf.inspector.bundles.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.ExportedPackage;
import org.osgi.service.packageadmin.PackageAdmin;

import com.github.stiepf.inspector.bundles.BundleQuery;

class BundleQueryImpl implements BundleQuery {

  private final BundleContext bundleContext;

  private List<BundlePredicate> predicates = new LinkedList<BundlePredicate>();

  private ServiceReference packageAdminReference;

  private PackageAdmin packageAdmin;

  BundleQueryImpl(BundleContext bundleContext) {
    this.bundleContext = bundleContext;
    packageAdminReference = bundleContext.getServiceReference(PackageAdmin.class.getName());
  }

  @Override
  public BundleQuery importsPackage(final String packageName) {
    return addPredicate(new BundlePredicate() {
      @Override
      boolean matches(Bundle bundle) {
        ExportedPackage[] exportedPackages = packageAdmin.getExportedPackages(packageName);
        if (exportedPackages != null) {
          for (ExportedPackage ep : exportedPackages) {
            List<Bundle> importingBundles = Arrays.asList(ep.getImportingBundles());
            if (importingBundles.contains(bundle))
              return true;
          }
        }
        return false;
      }
    });
  }

  @Override
  public BundleQuery exportsPackage(final String packageName) {
    return addPredicate(new BundlePredicate() {
      @Override
      boolean matches(Bundle bundle) {
        ExportedPackage[] exportedPackages = packageAdmin.getExportedPackages(bundle);
        if (exportedPackages != null) {
          for (ExportedPackage ep : exportedPackages) {
            if (ep.getName().equals(packageName))
              return true;
          }
        }
        return false;
      }
    });
  }

  @Override
  public BundleQuery providesService(final String serviceInterface) {
    return addPredicate(new BundlePredicate() {
      @Override
      boolean matches(Bundle bundle) {
        for (ServiceReference sr : referencesFor(serviceInterface)) {
          if (bundle.equals(sr.getBundle()))
            return true;
        }
        return false;
      }
    });
  }

  @Override
  public BundleQuery consumesService(final String serviceInterface) {
    return addPredicate(new BundlePredicate() {
      @Override
      boolean matches(Bundle bundle) {
        ServiceReference[] inUse = bundle.getServicesInUse();
        List<ServiceReference> available = referencesFor(serviceInterface);
        if (inUse != null) {
          for (ServiceReference sr : inUse) {
            if (available.contains(sr))
              return true;
          }
        }
        return false;
      }
    });
  }

  @Override
  public BundleQuery hasHeader(final String key, final String value) {
    return addPredicate(new BundlePredicate() {
      @Override
      boolean matches(Bundle bundle) {
        String headerValue = (String) bundle.getHeaders().get(key);
        return headerValue != null && headerValue.equals(value);
      }
    });
  }

  @Override
  public BundleQuery hasEntry(final String path) {
    return addPredicate(new BundlePredicate() {
      @Override
      boolean matches(Bundle bundle) {
        return bundle.getEntry(path) != null;
      }
    });
  }

  @Override
  public BundleQuery inState(final int bundleState) {
    return addPredicate(new BundlePredicate() {
      @Override
      boolean matches(Bundle bundle) {
        return bundleState == bundle.getState();
      }
    });
  }

  @Override
  public List<Bundle> list() {
    packageAdmin = (PackageAdmin) bundleContext.getService(packageAdminReference);
    List<Bundle> result = new LinkedList<Bundle>();
    Bundle[] bundles = bundleContext.getBundles();

    for (Bundle bundle : bundles) {
      if (allPredicatesMatch(bundle)) {
        result.add(bundle);
      }
    }
    bundleContext.ungetService(packageAdminReference);
    return result;
  }

  private boolean allPredicatesMatch(Bundle bundle) {
    for (BundlePredicate p : predicates) {
      if (!p.matches(bundle))
        return false;
    }
    return true;
  }

  private BundleQuery addPredicate(BundlePredicate predicate) {
    predicates.add(predicate);
    return this;
  }

  private List<ServiceReference> referencesFor(String serviceInterface) {
    List<ServiceReference> result = Collections.emptyList();
    try {
      ServiceReference[] serviceReferences = bundleContext.getServiceReferences(serviceInterface, null);
      if (serviceReferences != null) {
        result = Arrays.asList(serviceReferences);
      }
    } catch (InvalidSyntaxException e) {
      // Invalid syntax impossible for null argument
    }
    return result;
  }
}
