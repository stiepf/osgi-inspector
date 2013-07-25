package com.github.stiepf.inspector.bundles.internal;

import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.ExportedPackage;
import org.osgi.service.packageadmin.PackageAdmin;

class ExportsPackagePredicate extends BundlePredicate {

  private PackageAdmin packageAdmin;
  
  private String packageName;
  
  ExportsPackagePredicate(PackageAdmin packageAdmin, String packageName) {
    this.packageAdmin = packageAdmin;
    this.packageName = packageName;
  }

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

}
