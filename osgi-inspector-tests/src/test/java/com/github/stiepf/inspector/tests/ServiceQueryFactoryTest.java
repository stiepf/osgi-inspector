package com.github.stiepf.inspector.tests;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;

import com.github.stiepf.inspector.services.ServiceQuery;
import com.github.stiepf.inspector.services.ServiceQueryFactory;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ServiceQueryFactoryTest {

  @Inject
  private ServiceQueryFactory underTest;
  
  @Inject
  private PackageAdmin packageAdmin;
  
  @Configuration
  public Option[] configure() {
    return options(
        mavenBundle("com.github.stiepf.inspector", "osgi-inspector-services"),
        junitBundles()
    );
  }
  
  @Test
  public void providedBy() throws Exception {
    Bundle bundle = packageAdmin.getBundle(ServiceQuery.class);
    assertQuery(1, serviceQuery().providedBy(bundle));
  }
  
  @Test
  public void consumedBy() throws Exception {
    Bundle bundle = packageAdmin.getBundle(ServiceQueryFactoryTest.class);
    assertQuery(4, serviceQuery().consumedBy(bundle));
  }
  
  @Test
  public void hasInterface() throws Exception {
    assertQuery(1, serviceQuery().hasInterface(ServiceQueryFactory.class.getName()));
  }
  
  @Test
  public void hasProperty() throws Exception {
    assertQuery(1, serviceQuery().hasProperty("name"));
  }  
  
  @Test
  public void hasPropertyValue() throws Exception {
    assertQuery(1, serviceQuery().hasPropertyValue("name", "ServiceQueryService"));
  }
  
  @Test
  public void matchesFilter() throws Exception {
    assertQuery(1, serviceQuery().matchesFilter("(name=ServiceQueryService)"));
  }  
  
  private ServiceQuery serviceQuery() {
    return underTest.createServiceQuery();
  }
  
  private void assertQuery(int expected, ServiceQuery query) {
    List<ServiceReference> result = query.list();
    assertEquals(expected, result.size());
  }
}
