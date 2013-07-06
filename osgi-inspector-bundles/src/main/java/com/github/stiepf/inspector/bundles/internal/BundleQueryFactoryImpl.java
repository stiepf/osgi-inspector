package com.github.stiepf.inspector.bundles.internal;

import org.osgi.framework.BundleContext;

import com.github.stiepf.inspector.bundles.BundleQuery;
import com.github.stiepf.inspector.bundles.BundleQueryFactory;

public class BundleQueryFactoryImpl implements BundleQueryFactory {

  private BundleContext bundleContext;

  public BundleQueryFactoryImpl(BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  @Override
  public BundleQuery createBundleQuery() {
    return new BundleQueryImpl(bundleContext);
  }

}
