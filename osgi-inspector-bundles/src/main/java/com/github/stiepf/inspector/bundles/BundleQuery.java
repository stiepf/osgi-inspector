package com.github.stiepf.inspector.bundles;

import java.util.List;

import org.osgi.framework.Bundle;

public interface BundleQuery {

  BundleQuery importsPackage(String packageName);

  BundleQuery exportsPackage(String packageName);

  BundleQuery providesService(String serviceInterface);

  BundleQuery consumesService(String serviceInterface);

  BundleQuery hasHeader(String key, String value);

  BundleQuery hasEntry(String path);

  BundleQuery inState(int bundleState);

  List<Bundle> list();
}
