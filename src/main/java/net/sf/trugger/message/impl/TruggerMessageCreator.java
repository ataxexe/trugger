/*
 * Copyright 2009-2011 Marcelo Varella Barca Guimarães
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
package net.sf.trugger.message.impl;

import java.util.ResourceBundle;

import net.sf.trugger.element.Element;
import net.sf.trugger.message.Message;
import net.sf.trugger.message.MessageCreator;
import net.sf.trugger.message.MessageFormatter;
import net.sf.trugger.message.MessageResolver;
import net.sf.trugger.message.Messages;
import net.sf.trugger.util.Utils;

/**
 * The default implementation for MessageCreator.
 * 
 * @author Marcelo Varella Barca Guimarães
 */
public final class TruggerMessageCreator implements MessageCreator {
  
  /**
   * The ResourceBundle used to get the messages.
   */
  private ResourceBundle resourceBundle;
  /**
   * The object to get the messages from the bundle.
   */
  private MessageResolver messageResolver;
  /**
   * The object to format the messages.
   */
  private MessageFormatter messageFormatter;
  
  /**
   * Craetes a new message creator.
   * 
   * @param messageResolver
   *          the resolver to resolve the message.
   * @param messageFormatter
   *          the formatter to format the message.
   * @param resourceBundle
   *          the bundle to search for values.
   */
  TruggerMessageCreator(MessageResolver messageResolver, MessageFormatter messageFormatter,
      ResourceBundle resourceBundle) {
    this.messageResolver = messageResolver;
    this.messageFormatter = messageFormatter;
    this.resourceBundle = resourceBundle;
  }
  
  public Message createMessage(Element element, Object context, Object target) {
    String summary = messageResolver.getSummary(context, resourceBundle);
    String detail = messageResolver.getDetail(context, resourceBundle);
    if (!Utils.isEmpty(summary)) {
      summary = messageFormatter.format(summary, resourceBundle, element, context, target);
    }
    if (!Utils.isEmpty(detail)) {
      detail = messageFormatter.format(detail, resourceBundle, element, context, target);
    }
    return Utils.isEmpty(summary) && Utils.isEmpty(detail) ? null : Messages.newMessage(summary, detail);
  }
  
}
