package com.github.stiepf.inspector.bundles.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.ExportedPackage;
import org.osgi.service.packageadmin.PackageAdmin;

@RunWith(MockitoJUnitRunner.class)
public class ExportsPackagePredicateTest {

  private static final String PACKAGE_NAME = "com.github.stiepf.inspector.bundles";

  private BundlePredicate underTest;
  
  @Mock
  private PackageAdmin packageAdminMock;
  
  @Mock
  private Bundle bundleMock;

  @Mock
  private ExportedPackage exportedPackageMock;
  
  @Before
  public void setUp() {
    underTest = new ExportsPackagePredicate(packageAdminMock, PACKAGE_NAME);
  }
  
  @Test
  public void exportsPackageMatches() throws Exception {
    when(packageAdminMock.getExportedPackages(bundleMock)).thenReturn(new ExportedPackage[] {exportedPackageMock});
    when(exportedPackageMock.getName()).thenReturn(PACKAGE_NAME);
    assertTrue(underTest.matches(bundleMock));
  }

  @Test
  public void exportsPackageMatchesNot() throws Exception {
    when(packageAdminMock.getExportedPackages(bundleMock)).thenReturn(new ExportedPackage[] {exportedPackageMock});
    when(exportedPackageMock.getName()).thenReturn("some.dummy.package");
    assertFalse(underTest.matches(bundleMock));
  }
  
}
