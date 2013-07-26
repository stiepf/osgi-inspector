package com.github.stiepf.inspector.services.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.ServiceReference;

@RunWith(MockitoJUnitRunner.class)
public class HasPropertyPredicateTest {
  
  private static final String KEY = "KEY";
  
  private ServicePredicate underTest = new HasPropertyPredicate(KEY);
  
  @Mock
  private ServiceReference serviceReferenceMock;
  
  @Test
  public void hasPropertyMatches() throws Exception {
    when(serviceReferenceMock.getProperty(KEY)).thenReturn("VALUE");
    assertTrue(underTest.matches(serviceReferenceMock));
  }
  
  @Test
  public void hasPropertyMatchesNot() throws Exception {
    when(serviceReferenceMock.getProperty(KEY)).thenReturn(null);
    assertFalse(underTest.matches(serviceReferenceMock));
  }  
}
