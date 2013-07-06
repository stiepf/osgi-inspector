package com.github.stiepf.inspector.components;

import org.apache.aries.blueprint.PassThroughMetadata;
import org.osgi.service.blueprint.reflect.BeanMetadata;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.ServiceMetadata;
import org.osgi.service.blueprint.reflect.ServiceReferenceMetadata;

public enum ComponentType {
  UNKNOWN, BEAN, SERVICE, REFERENCE, IMPLICIT;

  public static ComponentType getComponentType(ComponentMetadata metadata) {
    if (metadata instanceof BeanMetadata) {
      return BEAN;
    } else if (metadata instanceof ServiceMetadata) {
      return SERVICE;
    } else if (metadata instanceof ServiceReferenceMetadata) {
      return REFERENCE;
    } else if (metadata instanceof PassThroughMetadata) {
      return IMPLICIT;
    }
    return UNKNOWN;
  }
}
