package com.github.stiepf.inspector.services;

import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

public interface ServiceQuery {

  ServiceQuery hasInterface(String serviceInterface);

  ServiceQuery providedBy(Bundle bundle);

  ServiceQuery consumedBy(Bundle bundle);

  ServiceQuery hasProperty(String key);

  ServiceQuery hasPropertyValue(String key, Object value);

  ServiceQuery matchesFilter(String filter);

  List<ServiceReference> list();
}
