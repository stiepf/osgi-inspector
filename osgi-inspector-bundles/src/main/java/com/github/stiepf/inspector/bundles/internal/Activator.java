package com.github.stiepf.inspector.bundles.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.github.stiepf.inspector.bundles.BundleQueryFactory;

public class Activator implements BundleActivator {

  private ServiceRegistration factoryRegistration;

  @Override
  public void start(BundleContext context) throws Exception {
    BundleQueryFactory factory = new BundleQueryFactoryImpl(context);
    factoryRegistration = context.registerService(BundleQueryFactory.class.getName(), factory, null);
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    factoryRegistration.unregister();
  }

}
