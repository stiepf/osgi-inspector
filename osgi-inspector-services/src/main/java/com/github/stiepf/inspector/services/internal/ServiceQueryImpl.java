package com.github.stiepf.inspector.services.internal;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.github.stiepf.inspector.services.ServiceQuery;

public class ServiceQueryImpl implements ServiceQuery {

  private BundleContext bundleContext;

  private List<ServicePredicate> predicates = new LinkedList<ServicePredicate>();

  private String knownInterface;

  private String filter;

  public ServiceQueryImpl(BundleContext bundleContext) {
    this.bundleContext = bundleContext;
  }

  @Override
  public ServiceQuery hasInterface(String serviceInterface) {
    knownInterface = serviceInterface;
    return this;
  }

  @Override
  public ServiceQuery providedBy(final Bundle bundle) {
    return addPredicate(new ServicePredicate() {
      @Override
      public boolean matches(ServiceReference reference) {
        return reference.getBundle().equals(bundle);
      }
    });
  }

  @Override
  public ServiceQuery consumedBy(final Bundle bundle) {
    return addPredicate(new ServicePredicate() {
      @Override
      public boolean matches(ServiceReference reference) {
        Bundle[] usingBundles = reference.getUsingBundles();
        if (usingBundles != null) {
          return Arrays.asList(usingBundles).contains(bundle);
        } else {
          return false;
        }

      }
    });
  }

  @Override
  public ServiceQuery hasProperty(final String key) {
    return addPredicate(new ServicePredicate() {
      @Override
      public boolean matches(ServiceReference reference) {
        return reference.getProperty(key) != null;
      }
    });
  }

  @Override
  public ServiceQuery hasPropertyValue(final String key, final Object value) {
    return addPredicate(new ServicePredicate() {
      @Override
      public boolean matches(ServiceReference reference) {
        Object property = reference.getProperty(key);
        if (property != null) {
          return property.equals(value);
        } else {
          return false;
        }

      }
    });
  }

  @Override
  public ServiceQuery matchesFilter(String filter) {
    this.filter = filter;
    return this;
  }

  @Override
  public List<ServiceReference> list() {
    List<ServiceReference> result = new LinkedList<ServiceReference>();

    ServiceReference[] references;
    try {
      references = bundleContext.getAllServiceReferences(knownInterface, filter);
    } catch (InvalidSyntaxException e) {
      references = new ServiceReference[0];
    }

    for (ServiceReference sr : references) {
      if (allPredicatesMatch(sr)) {
        result.add(sr);
      }
    }
    return result;
  }

  private boolean allPredicatesMatch(ServiceReference sr) {
    for (ServicePredicate sp : predicates) {
      if (!sp.matches(sr))
        return false;
    }
    return true;
  }

  private ServiceQuery addPredicate(ServicePredicate predicate) {
    predicates.add(predicate);
    return this;
  }
}
