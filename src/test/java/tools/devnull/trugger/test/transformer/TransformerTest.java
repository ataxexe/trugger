/*
 * Copyright 2009-2012 Marcelo Varella Barca Guimarães
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tools.devnull.trugger.test.transformer;

import tools.devnull.trugger.element.Elements;
import tools.devnull.trugger.transformer.Transformer;
import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

import static tools.devnull.trugger.transformer.Transformers.BOOLEAN;
import static tools.devnull.trugger.transformer.Transformers.DOUBLE;
import static tools.devnull.trugger.transformer.Transformers.FLOAT;
import static tools.devnull.trugger.transformer.Transformers.INTEGER;
import static tools.devnull.trugger.transformer.Transformers.LONG;
import static tools.devnull.trugger.transformer.Transformers.PROPERTIES;
import static tools.devnull.trugger.transformer.Transformers.STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Marcelo Varella Barca Guimarães
 */
public class TransformerTest {

  static class MailConfig {
    boolean sendMail;
    Boolean checkMail;

    int retries;
    float delay;

    String user;
    String password;

    long timestamp;
    double value;
  }

  @Test
  public void testPropertiesTransformer() throws Exception {
    Properties props = new Properties();
    InputStream is = getClass().getResourceAsStream("test.properties");
    props.load(is);
    is.close();

    MailConfig config = new MailConfig();

    Elements.copy().from(props).as(PROPERTIES).to(config);

    assertTrue(config.sendMail);
    assertFalse(config.checkMail);

    assertEquals(2, config.retries);
    assertEquals(1.5, config.delay, 1e-5);

    assertEquals("mailUser", config.user);
    assertEquals("mailPassword", config.password);
  }

  @Test
  public void testTransformers() throws Exception {
    assertTrue(BOOLEAN.transform("true"));
    assertFalse(BOOLEAN.transform("false"));

    assertEquals(Integer.valueOf(1), INTEGER.transform("1"));
    assertEquals(Long.valueOf(1), LONG.transform("1"));
    assertEquals(Double.valueOf(2.5), DOUBLE.transform("2.5"));
    assertEquals(Float.valueOf(2.5f), FLOAT.transform("2.5"));
    assertEquals("1", STRING.transform(1));
  }

  private static class MockTransformer implements Transformer {

    private Object returnValue;

    public MockTransformer(Object returnValue) {
      this.returnValue = returnValue;
    }

    public Object transform(Object object) {
      return returnValue;
    }

  }

}
