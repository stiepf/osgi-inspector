package com.github.stiepf.inspector.services.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

@RunWith(MockitoJUnitRunner.class)
public class ProvidedByPredicateTest {

  private ServicePredicate underTest;
  
  @Mock
  private Bundle bundleMock;
  
  @Mock
  private Bundle other;  
  
  @Mock
  private ServiceReference serviceReferenceMock;
  
  @Before
  public void setUp() {
    underTest = new ProvidedByPredicate(bundleMock);
  }
  
  @Test
  public void providedByMatches() throws Exception {
    when(serviceReferenceMock.getBundle()).thenReturn(bundleMock);
    assertTrue(underTest.matches(serviceReferenceMock));
  }
  
  @Test
  public void providedByMatchesNot() throws Exception {
    when(serviceReferenceMock.getBundle()).thenReturn(other);
    assertFalse(underTest.matches(serviceReferenceMock));
  }
  
}
