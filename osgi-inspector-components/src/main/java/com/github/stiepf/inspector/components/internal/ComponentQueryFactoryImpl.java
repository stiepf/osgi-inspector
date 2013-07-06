package com.github.stiepf.inspector.components.internal;

import org.osgi.framework.BundleContext;

import com.github.stiepf.inspector.components.ComponentQuery;
import com.github.stiepf.inspector.components.ComponentQueryFactory;

public class ComponentQueryFactoryImpl implements ComponentQueryFactory {

  private BundleContext bundleContext;

  public ComponentQueryFactoryImpl(BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  @Override
  public ComponentQuery createComponentQuery() {
    return new ComponentQueryImpl(bundleContext);
  }

}
