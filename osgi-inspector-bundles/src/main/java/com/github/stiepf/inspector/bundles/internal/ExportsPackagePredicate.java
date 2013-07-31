package com.github.stiepf.inspector.bundles.internal;

import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.ExportedPackage;

class ExportsPackagePredicate extends BundlePredicate {

  private PackageAdminProvider pap;
  
  private String packageName;
  
  ExportsPackagePredicate(PackageAdminProvider packageAdminProvider, String packageName) {
    this.pap = packageAdminProvider;
    this.packageName = packageName;
  }

  @Override
  boolean matches(Bundle bundle) {
    ExportedPackage[] exportedPackages = pap.getPackageAdmin().getExportedPackages(bundle);
    if (exportedPackages != null) {
      for (ExportedPackage ep : exportedPackages) {
        if (ep.getName().equals(packageName))
          return true;
      }
    }
    return false;
  }

}
