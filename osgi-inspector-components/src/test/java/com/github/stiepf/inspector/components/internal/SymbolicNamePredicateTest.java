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
public class SymbolicNamePredicateTest {

  private static final String SYMBOLIC_NAME = "com.github.stiepf.inspector.components";
  
  private ServiceReferencePredicate underTest = new SymbolicNamePredicate(SYMBOLIC_NAME);
  
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private ServiceReference serviceReferenceMock;
  
  @Test
  public void bundleIdMatches() throws Exception {
    when(serviceReferenceMock.getBundle().getSymbolicName()).thenReturn(SYMBOLIC_NAME);
    assertTrue(underTest.matches(serviceReferenceMock));
  }
  
  @Test
  public void bundleIdMatchesNot() throws Exception {
    when(serviceReferenceMock.getBundle().getSymbolicName()).thenReturn("OTHER");
    assertFalse(underTest.matches(serviceReferenceMock));
  }
  
}
