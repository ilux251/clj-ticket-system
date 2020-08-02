(defproject ticket-system "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [hiccup "1.0.5"]
                 [http-kit "2.3.0"]
                 [ring/ring-json "0.5.0"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [clojureql "1.0.5"]
                 [org.clojure/java.jdbc "0.7.5"]]
  
  :main ^:skip-aot ticket-system.core
  
  :target-path "target/%s"
  
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[ring/ring-mock "0.3.2"]]
                   :repl-options {:init-ns ticket-system.core}}}
  
  :repl-options {:init-ns ticket-system.core})
