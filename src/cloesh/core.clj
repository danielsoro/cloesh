; JBoss, Home of Professional Open Source
; Copyright 2014 Red Hat Inc. and/or its affiliates and other contributors
; as indicated by the @authors tag. All rights reserved.
; See the copyright.txt in the distribution for a
; full listing of individual contributors.
;
; Licensed under the Apache License, Version 2.0 (the "License");
; you may not use this file except in compliance with the License.
; You may obtain a copy of the License at
; http://www.apache.org/licenses/LICENSE-2.0
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an "AS IS" BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.

(ns cloesh.core
  (:import (org.jboss.aesh.console                  AeshConsoleBuilder Prompt)
           (org.jboss.aesh.console.command.registry AeshCommandRegistryBuilder)
           (org.jboss.aesh.console.settings         SettingsBuilder)
           (org.jboss.aesh.extensions.cat           Cat)
           (org.jboss.aesh.extensions.cd            Cd)
           (org.jboss.aesh.extensions.clear         Clear)
           (org.jboss.aesh.extensions.echo          Echo)
           (org.jboss.aesh.extensions.harlem.aesh   Harlem)
           (org.jboss.aesh.extensions.less.aesh     Less)
           (org.jboss.aesh.extensions.ls            Ls)
           (org.jboss.aesh.extensions.matrix        Matrix)
           (org.jboss.aesh.extensions.mkdir         Mkdir)
           (org.jboss.aesh.extensions.more.aesh     More)
           (org.jboss.aesh.extensions.pwd           Pwd)
           (org.jboss.aesh.extensions.rm            Rm)
           (org.jboss.aesh.extensions.touch         Touch))
  (:gen-class))

(defn init-settings-builder []
  (doto (SettingsBuilder.) (.readInputrc false) (.logging true)))

(defn init-aesh-command-registry-builder []
  (doto (AeshCommandRegistryBuilder.)
        (.commands (into-array Class [Cat Cd Clear Echo Harlem Less Ls Matrix Mkdir More Pwd Rm Touch]))))

(defn -main
  [& args]
  (println "Cloesh wrapping Æsh for you in Clojure!\n")
  (let [sb   (init-settings-builder)
        acrb (init-aesh-command-registry-builder)]
      (let [acb (doto (AeshConsoleBuilder.) (.commandRegistry (.create acrb))
                                            (.settings (.create sb))
                                            (.prompt (Prompt. "[cloesh@~]$ ")))]
        (.start (.create acb)))))