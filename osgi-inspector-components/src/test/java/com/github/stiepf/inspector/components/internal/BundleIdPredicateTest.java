package com.github.stiepf.inspector.components.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.ServiceReference;

@RunWith(MockitoJUnitRunner.class)
public class BundleIdPredicateTest {

  private static final long BUNDLE_ID = 1001L;
  
  private ServiceReferencePredicate underTest = new BundleIdPredicate(BUNDLE_ID);
  
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private ServiceReference serviceReferenceMock;
  
  @Test
  public void bundleIdMatches() throws Exception {
    when(serviceReferenceMock.getBundle().getBundleId()).thenReturn(BUNDLE_ID);
    assertTrue(underTest.matches(serviceReferenceMock));
  }
  
  @Test
  public void bundleIdMatchesNot() throws Exception {
    when(serviceReferenceMock.getBundle().getBundleId()).thenReturn(1002L);
    assertFalse(underTest.matches(serviceReferenceMock));
  }
  
}
