package com.github.stiepf.inspector.bundles.internal;

import org.osgi.framework.Bundle;

class HasHeaderPredicate extends BundlePredicate {

  private String key;
  
  private String value;
  
  HasHeaderPredicate(String key, String value) {
    this.key = key;
    this.value = value;
  }

  @Override
  boolean matches(Bundle bundle) {
    String headerValue = (String) bundle.getHeaders().get(key);
    return headerValue != null && headerValue.equals(value);
  }

}
