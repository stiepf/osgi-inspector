package com.github.stiepf.inspector.components.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.github.stiepf.inspector.components.ComponentQueryFactory;

public class Activator implements BundleActivator {

    private ServiceRegistration serviceRegistration;
    
    @Override
    public void start(BundleContext context) throws Exception {
        ComponentQueryFactory bqf = new ComponentQueryFactoryImpl(context);
        serviceRegistration = context.registerService(ComponentQueryFactory.class.getName(), bqf, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        serviceRegistration.unregister();
    }

}
