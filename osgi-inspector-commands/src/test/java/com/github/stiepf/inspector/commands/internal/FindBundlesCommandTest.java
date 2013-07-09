package com.github.stiepf.inspector.commands.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.felix.service.command.CommandSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import com.github.stiepf.inspector.bundles.BundleQuery;
import com.github.stiepf.inspector.bundles.BundleQueryFactory;

@RunWith(MockitoJUnitRunner.class)
public class FindBundlesCommandTest {
  
  private static final String PACKAGE_NAME = "sun.misc";
  private static final String SERVICE_INTERFACE = "com.github.stiepf.inspector.bundles.BundleQueryFactory";
  private static final String ENTRY_PATH ="META-INF/MANIFEST.MF";
  private static final String HEADER ="Dummy: Dummy-Header";

  @InjectMocks
  private FindBundlesCommand underTest = new FindBundlesCommand();
  
  @Mock
  private BundleQueryFactory bundleQueryFactoryMock;
  
  @Mock
  private BundleQuery bundleQueryMock;
  
  @Mock
  private CommandSession sessionMock;
  
  @Test
  public void bundleExports() throws Exception {
    underTest.exports = PACKAGE_NAME;
    execute();
    verify(bundleQueryMock).exportsPackage(PACKAGE_NAME);
  }
  
  @Test
  public void bundleImports() throws Exception {
    underTest.imports = PACKAGE_NAME;
    execute();
    verify(bundleQueryMock).importsPackage(PACKAGE_NAME);
  }

  @Test
  public void bundleConsumes() throws Exception {
    underTest.consumes = SERVICE_INTERFACE;
    execute();
    verify(bundleQueryMock).consumesService(SERVICE_INTERFACE);
  }
  
  @Test
  public void bundleProvides() throws Exception {
    underTest.provides = SERVICE_INTERFACE;
    execute();
    verify(bundleQueryMock).providesService(SERVICE_INTERFACE);
  }
  
  @Test
  public void bundleEntry() throws Exception {
    underTest.entry = ENTRY_PATH;
    execute();
    verify(bundleQueryMock).hasEntry(ENTRY_PATH);
  }
  
  @Test
  public void bundleHeader() throws Exception {
    underTest.header = HEADER;
    execute();
    verify(bundleQueryMock).hasHeader("Dummy", "Dummy-Header");
  }
  
  @Test
  public void combinedQuery() throws Exception {
    underTest.exports = PACKAGE_NAME;
    underTest.imports = PACKAGE_NAME;
    underTest.consumes = SERVICE_INTERFACE;
    underTest.provides = SERVICE_INTERFACE;
    underTest.entry = ENTRY_PATH;
    underTest.header = HEADER;
    execute();
    verify(bundleQueryMock).exportsPackage(PACKAGE_NAME);
    verify(bundleQueryMock).importsPackage(PACKAGE_NAME);
    verify(bundleQueryMock).consumesService(SERVICE_INTERFACE);
    verify(bundleQueryMock).providesService(SERVICE_INTERFACE);
    verify(bundleQueryMock).hasEntry(ENTRY_PATH);
    verify(bundleQueryMock).hasHeader("Dummy", "Dummy-Header");
  }
  
  private void execute() throws Exception {
    List<Bundle> bundles = new ArrayList<Bundle>();
    bundles.add(mockBundle());
    
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(buffer);
    when(bundleQueryFactoryMock.createBundleQuery()).thenReturn(bundleQueryMock);
    when(bundleQueryMock.list()).thenReturn(bundles);
    when(sessionMock.getConsole()).thenReturn(out);
    
    Object result = underTest.execute(sessionMock);
    assertTrue(buffer.toString().contains("[111][Active  ][Dummy (1.1.1)]"));
    assertNull(result);
  }
  
  private Bundle mockBundle() {
    Dictionary<String, String> headers = new Hashtable<String, String>();
    headers.put("Bundle-Name", "Dummy");
    Bundle b = mock(Bundle.class);
    when(b.getBundleId()).thenReturn(111L);
    when(b.getState()).thenReturn(Bundle.ACTIVE);
    when(b.getHeaders()).thenReturn(headers);
    when(b.getVersion()).thenReturn(new Version("1.1.1"));
    return b;
  }
}
