/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.stiepf.inspector.services;

import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

/**
 * Represents the means to build a query by adding criteria and run it against an OSGi framework implementation.
 * Running the execute() method must return a valid result at any time, i.e. the result will include all known services, 
 * if no limiting criteria are active or an empty list, if none matches the given criteria. Every bundle in the 
 * result must match all criteria.
 * 
 * @author stiepf@googlemail.com
 *
 */
public interface ServiceQuery {

  /**
   * Limits the result to services implementing the given interface. Consecutive calls will overwrite the value.
   * 
   * @param serviceInterface
   *  the full qualified name of the service interface class
   * @return
   *  the query itself
   */
  ServiceQuery hasInterface(String serviceInterface);

  /**
   * Limits the result to services registered by the given bundle. 
   * 
   * @param bundle
   *  the bundle
   * @return
   *  the query itself
   */
  ServiceQuery providedBy(Bundle bundle);

  /**
   * Limits the result to services consumed by the given bundle. Results of consecutive calls may differ over time due 
   * to the dynamic nature of OSGi services.
   * 
   * @param bundle
   *  the bundle
   * @return
   *  the query itself
   */
  ServiceQuery consumedBy(Bundle bundle);

  /**
   * Limits the result to services registered with the given service property.
   * 
   * @param key
   *  the property's name
   * @return
   *  the query itself
   */
  ServiceQuery hasProperty(String key);


  /**
   * Limits the result to services registered with the given service property AND a matching value.
   * 
   * @param key
   *  the property's name
   * @param value
   *  the property's value
   * @return
   * the query itself
   */
  ServiceQuery hasPropertyValue(String key, Object value);

  /**
   * Limits the result to services matching the given LDAP filter string as defined by the OSGi spec. Consecutive calls 
   * will overwrite previous values.
   * 
   * @param key
   *  the property's name
   * @return
   *  the query itself
   */
  ServiceQuery matchesFilter(String filter);

  
  /**
   * Returns a list of all services matching the previously added criteria. You may call execute() repeatedly 
   * and add further criteria between the consecutive calls, or overwrite them in case of hasInterface() and 
   * matchesFilter().
   * 
   * @return
   *  list of matching ServiceReference objects
   */
  List<ServiceReference> execute();
}
