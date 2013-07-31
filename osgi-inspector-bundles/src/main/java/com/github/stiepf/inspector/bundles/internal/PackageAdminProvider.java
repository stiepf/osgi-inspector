package com.github.stiepf.inspector.bundles.internal;

import org.osgi.service.packageadmin.PackageAdmin;

abstract class PackageAdminProvider {

  abstract PackageAdmin getPackageAdmin();
  
}
