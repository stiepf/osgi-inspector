package com.github.stiepf.inspector.services.internal;

import org.osgi.framework.BundleContext;

import com.github.stiepf.inspector.services.ServiceQuery;
import com.github.stiepf.inspector.services.ServiceQueryFactory;

public class ServiceQueryFactoryImpl implements ServiceQueryFactory {

  private BundleContext bundleContext;

  public ServiceQueryFactoryImpl(BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  @Override
  public ServiceQuery createServiceQuery() {
    return new ServiceQueryImpl(bundleContext);
  }

}
