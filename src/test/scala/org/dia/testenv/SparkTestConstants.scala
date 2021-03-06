/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dia.testenv

import scala.collection.mutable
import scala.io.Source

import org.dia.core.SciSparkContext

object SparkTestConstants {
  val sc = new SciSparkContext("local[4]", "test")
  val datasetPath = "src/test/resources/TestLinks2.txt"
  val datasetVariable = "precipitation"

  val credentialsFilePath = "src/test/resources/TestHTTPCredentials"
  val credentialList = Source.fromFile(credentialsFilePath)
    .getLines()
    .map(p => {
      val split = p.split("\\s+")
      (split(0), split(1))
    })
  val testHTTPCredentials = new mutable.LinkedHashMap[String, String] ++= credentialList

}
