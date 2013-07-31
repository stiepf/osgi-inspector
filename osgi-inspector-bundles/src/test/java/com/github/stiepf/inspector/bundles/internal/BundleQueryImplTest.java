package com.github.stiepf.inspector.bundles.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;

@RunWith(MockitoJUnitRunner.class)
public class BundleQueryImplTest {

  private BundleQueryImpl underTest;
  
  @Mock
  private BundleContext bundleContextMock;
  
  @Mock
  private ServiceReference serviceReferenceMock;
  
  @Mock
  private PackageAdmin packageAdminMock;
  
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private Bundle bundleMock1;
  
  @Mock
  private Bundle bundleMock2;
  
  
  @Before
  public void setUp() {
    underTest = new BundleQueryImpl(bundleContextMock);
    when(bundleContextMock.getServiceReference(PackageAdmin.class.getName())).thenReturn(serviceReferenceMock);
  }
  
  @Test
  public void initialEmptyPredicates() throws Exception {
    assertEquals(0, underTest.getPredicates().size());
  }
  
  @Test
  public void executeIncludesMatchingBundles() throws Exception {
    BundlePredicate predicate = mockPredicate();
    when(predicate.matches(bundleMock1)).thenReturn(true);
    when(predicate.matches(bundleMock2)).thenReturn(true);
    expectList(bundleMock1, bundleMock2);
    List<Bundle> result = underTest.execute();
    
    assertEquals(2, result.size());
    assertSame(bundleMock1, result.get(0));
  }
  
  @Test
  public void executeExcludesNonMatchingBundles() throws Exception {
    BundlePredicate predicate = mockPredicate();
    when(predicate.matches(bundleMock1)).thenReturn(false);
    when(predicate.matches(bundleMock2)).thenReturn(true);
    expectList(bundleMock1, bundleMock2);
    List<Bundle> result = underTest.execute();
    
    assertEquals(1, result.size());
    assertSame(bundleMock2, result.get(0));
  }
      
  private BundlePredicate mockPredicate() {
    BundlePredicate predicate = mock(BundlePredicate.class);
    underTest.getPredicates().add(predicate);
    return predicate;
  }

  private void expectList(Bundle... bundles) {
    when(bundleContextMock.getService(serviceReferenceMock)).thenReturn(packageAdminMock);
    when(bundleContextMock.getBundles()).thenReturn(bundles);
  }
  
}
