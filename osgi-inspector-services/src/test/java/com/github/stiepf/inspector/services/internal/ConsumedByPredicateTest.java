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
public class ConsumedByPredicateTest {

  private ServicePredicate underTest;
  
  @Mock
  private Bundle bundleMock;
  
  @Mock
  private Bundle other;  
  
  @Mock
  private ServiceReference serviceReferenceMock;
  
  @Before
  public void setUp() {
    underTest = new ConsumedByPredicate(bundleMock);
  }
  
  @Test
  public void consumedByMatches() throws Exception {
    when(serviceReferenceMock.getUsingBundles()).thenReturn(new Bundle[] {bundleMock, other});
    assertTrue(underTest.matches(serviceReferenceMock));
  }
  
  @Test
  public void consumedByMatchesNotForOther() throws Exception {
    when(serviceReferenceMock.getUsingBundles()).thenReturn(new Bundle[] {other});
    assertFalse(underTest.matches(serviceReferenceMock));
  }
  
  @Test
  public void consumedByMatchesNotForNull() throws Exception {
    when(serviceReferenceMock.getUsingBundles()).thenReturn(null);
    assertFalse(underTest.matches(serviceReferenceMock));
  }  
}
