package com.github.stiepf.inspector.bundles.internal;

import java.util.Arrays;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.ExportedPackage;

class ImportsPackagePredicate extends BundlePredicate {

  private PackageAdminProvider pap;
  
  private String packageName;
  
  ImportsPackagePredicate(PackageAdminProvider packageAdminProvider, String packageName) {
    this.pap = packageAdminProvider;
    this.packageName = packageName;
  }

  @Override
  boolean matches(Bundle bundle) {
    ExportedPackage[] exportedPackages = pap.getPackageAdmin().getExportedPackages(packageName);
    if (exportedPackages != null) {
      for (ExportedPackage ep : exportedPackages) {
        List<Bundle> importingBundles = Arrays.asList(ep.getImportingBundles());
        if (importingBundles.contains(bundle))
          return true;
      }
    }
    return false;
  }

}
