package com.github.stiepf.inspector.bundles.internal;

import java.util.Arrays;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.ExportedPackage;
import org.osgi.service.packageadmin.PackageAdmin;

class ImportsPackagePredicate extends BundlePredicate {

  private PackageAdmin packageAdmin;
  
  private String packageName;
  
  ImportsPackagePredicate(PackageAdmin packageAdmin, String packageName) {
    this.packageAdmin = packageAdmin;
    this.packageName = packageName;
  }

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

}
