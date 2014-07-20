/*
 * Copyright 2009-2012 Marcelo Guimarães
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
package tools.devnull.trugger.scan.impl;

import tools.devnull.trugger.scan.ClassScanningException;
import tools.devnull.trugger.scan.ResourceFinder;
import tools.devnull.trugger.scan.ScanLevel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * A finder for file resources inside a JBoss AS 5.x and 6.x
 *
 * @author Marcelo Guimarães
 * @since 2.8
 */
public class VfsFileResourceFinder implements ResourceFinder {

  private static final Pattern PATTERN = Pattern.compile("vfsfile");

  private final ResourceFinder fileResourceFinder = new FileResourceFinder();

  @Override
  public String protocol() {
    return "vfsfile";
  }

  @Override
  public Set<String> find(URL resource, String packageName, ScanLevel scanLevel) {
    try {
      URL dirUrl = new URL(PATTERN.matcher(resource.toString()).replaceFirst("file"));
      return fileResourceFinder.find(dirUrl, packageName, scanLevel);
    } catch (MalformedURLException e) {
      throw new ClassScanningException(e);
    } catch (IOException e) {
      throw new ClassScanningException(e);
    }
  }

}
