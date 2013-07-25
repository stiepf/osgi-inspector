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
public class ProvidesServicePredicateTest {

  private static final String SERVICE_INTERFACE = "com.github.stiepf.inspector.bundles.BundleQueryFactory";
  
  private BundlePredicate underTest = new ProvidesServicePredicate(SERVICE_INTERFACE);
  
  @Mock
  private Bundle bundleMock;
  
  @Mock
  private Bundle other;

 
  @Mock
  private BundleContext bundleContextMock;
  
  @Mock
  private ServiceReference serviceReferenceMock;
  
  @Test
  public void providesServiceMatches() throws Exception {
    when(bundleMock.getBundleContext()).thenReturn(bundleContextMock);
    when(bundleContextMock.getServiceReferences(SERVICE_INTERFACE, null))
      .thenReturn(new ServiceReference[] {serviceReferenceMock});
    when(serviceReferenceMock.getBundle()).thenReturn(bundleMock);
    assertTrue(underTest.matches(bundleMock));
  }
  
  @Test
  public void providesServiceMatchesNot() throws Exception {
    when(bundleMock.getBundleContext()).thenReturn(bundleContextMock);
    when(bundleContextMock.getServiceReferences(SERVICE_INTERFACE, null))
      .thenReturn(new ServiceReference[] {serviceReferenceMock});
    when(serviceReferenceMock.getBundle()).thenReturn(other);
    assertFalse(underTest.matches(bundleMock));
  }

}
