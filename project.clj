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
                 [org.clojure/java.jdbc "0.7.5"]
                 
                 [reagent "0.8.1"]
                 [re-frame "0.10.6"]]
  
  :main ^:skip-aot ticket-system.server.core
  
  :target-path "target/%s"
  
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[ring/ring-mock "0.3.2"]
                                  [org.clojure/clojurescript "1.10.520"]
                                  [com.bhauman/figwheel-main "0.2.0"]
                                  [cider/piggieback "0.4.0"]]
                   :source-paths ["dev"]
                   :repl-options {:init-ns ticket-system.server.core
                                  :nrepl-middleware [cider.piggieback/wrap-cljs-repl]}}}
  
  :repl-options {:init-ns ticket-system.server.core})
