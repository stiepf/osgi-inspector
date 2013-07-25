package com.github.stiepf.inspector.bundles.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

@RunWith(MockitoJUnitRunner.class)
public class ConsumesServicePredicateTest {

  private static final String SERVICE_INTERFACE = "com.github.stiepf.inspector.bundles.BundleQueryFactory";
  
  private BundlePredicate underTest = new ConsumesServicePredicate(SERVICE_INTERFACE);
  
  @Mock
  private Bundle bundleMock;
  
  @Mock
  private BundleContext bundleContextMock;
  
  @Mock
  private ServiceReference serviceReferenceMock;
  
  @Test
  public void consumesServiceMatches() throws Exception {
    when(bundleMock.getServicesInUse()).thenReturn(new ServiceReference[] {serviceReferenceMock});
    when(bundleMock.getBundleContext()).thenReturn(bundleContextMock);
    when(bundleContextMock.getServiceReferences(SERVICE_INTERFACE, null))
      .thenReturn(new ServiceReference[] {serviceReferenceMock});
    assertTrue(underTest.matches(bundleMock));
  }
  
  @Test
  public void consumesServiceMatchesNot() throws Exception {
    when(bundleMock.getServicesInUse()).thenReturn(new ServiceReference[] {});
    when(bundleMock.getBundleContext()).thenReturn(bundleContextMock);
    when(bundleContextMock.getServiceReferences(SERVICE_INTERFACE, null))
      .thenReturn(new ServiceReference[] {serviceReferenceMock});
    assertFalse(underTest.matches(bundleMock));
  }

}
