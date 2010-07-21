/*
 * Copyright 2009-2010 Marcelo Varella Barca Guimarães
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
package net.sf.trugger.test.ui.swing;

import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.trugger.annotation.Bind;
import net.sf.trugger.ui.swing.SwingBind;

/**
 * @author Marcelo Varella Barca Guimarães
 */
@SwingBind
public class AddressPanel extends JPanel {

  @Bind
  JTextField street = new JTextField();

  @Bind
  JTextField city = new JTextField();

  @Bind
  JTextField state = new JTextField();

  public void reset() {
    street.setText("");
    city.setText("");
    state.setText("");
  }

}