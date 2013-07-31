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

import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;

import com.github.stiepf.inspector.bundles.BundleQuery;

class BundleQueryImpl extends PackageAdminProvider implements BundleQuery {

  private final BundleContext bundleContext;

  private List<BundlePredicate> predicates = new LinkedList<BundlePredicate>();

  private ServiceReference packageAdminReference;

  private PackageAdmin packageAdmin;

  BundleQueryImpl(BundleContext bundleContext) {
    this.bundleContext = bundleContext;
    packageAdminReference = bundleContext.getServiceReference(PackageAdmin.class.getName());
  }

  @Override
  public BundleQuery importsPackage(String packageName) {
    return addPredicate(new ImportsPackagePredicate(this, packageName));
  }

  @Override
  public BundleQuery exportsPackage(final String packageName) {
    return addPredicate(new ExportsPackagePredicate(this, packageName));
  }

  @Override
  public BundleQuery providesService(final String serviceInterface) {
    return addPredicate(new ProvidesServicePredicate(serviceInterface));
  }

  @Override
  public BundleQuery consumesService(final String serviceInterface) {
    return addPredicate(new ConsumesServicePredicate(serviceInterface));
  }

  @Override
  public BundleQuery hasHeader(final String key, final String value) {
    return addPredicate(new HasHeaderPredicate(key, value));
  }

  @Override
  public BundleQuery hasEntry(final String path) {
    return addPredicate(new HasEntryPredicate(path));
  }

  @Override
  public BundleQuery inState(final int bundleState) {
    return addPredicate(new InStatePredicate(bundleState));
  }

  @Override
  public List<Bundle> execute() {
    retrieveService();
    
    List<Bundle> result = new LinkedList<Bundle>();
    Bundle[] bundles = bundleContext.getBundles();

    for (Bundle bundle : bundles) {
      if (allPredicatesMatch(bundle)) {
        result.add(bundle);
      }
    }
    
    releaseService();
    return result;
  }

  @Override
  PackageAdmin getPackageAdmin() {
    return packageAdmin;
  }
  
  List<BundlePredicate> getPredicates() {
    return predicates;
  }

  private void retrieveService() {
    packageAdmin = (PackageAdmin) bundleContext.getService(packageAdminReference);
  }

  private void releaseService() {
    bundleContext.ungetService(packageAdminReference);
    packageAdmin = null;
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
  
}
