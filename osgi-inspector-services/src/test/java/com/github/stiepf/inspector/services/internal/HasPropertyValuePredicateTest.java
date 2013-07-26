package com.github.stiepf.inspector.services.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.ServiceReference;

@RunWith(MockitoJUnitRunner.class)
public class HasPropertyValuePredicateTest {

  private static final String KEY = "KEY";

  private static final Integer VALUE = new Integer("1");
  
  private ServicePredicate underTest = new HasPropertyValuePredicate(KEY, VALUE);
  
  @Mock
  private ServiceReference serviceReferenceMock;
  
  @Test
  public void hasPropertyValueMatches() throws Exception {
    when(serviceReferenceMock.getProperty(KEY)).thenReturn(VALUE);
    assertTrue(underTest.matches(serviceReferenceMock));
  }
  
  @Test
  public void hasPropertyValueMatchesNotForNull() throws Exception {
    when(serviceReferenceMock.getProperty(KEY)).thenReturn(null);
    assertFalse(underTest.matches(serviceReferenceMock));
  }  
  
  @Test
  public void hasPropertyValueMatchesNotForOther() throws Exception {
    when(serviceReferenceMock.getProperty(KEY)).thenReturn("OTHER");
    assertFalse(underTest.matches(serviceReferenceMock));
  }  
}
