package com.github.stiepf.inspector.services.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.github.stiepf.inspector.services.ServiceQueryFactory;

public class Activator implements BundleActivator {

  private ServiceRegistration factoryRegistration;

  @Override
  public void start(BundleContext context) throws Exception {
    ServiceQueryFactory factory = new ServiceQueryFactoryImpl(context);
    factoryRegistration = context.registerService(ServiceQueryFactory.class.getName(), factory, null);
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    factoryRegistration.unregister();
  }

}
