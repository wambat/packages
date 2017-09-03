(set-env!
 :resource-paths #{"resources"}
 :dependencies '[[cljsjs/boot-cljsjs "0.7.0"  :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "1.30.0")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom {:project     'cljsjs/plotly
      :version     +version+
      :description "The open source javascript graphing library that powers plotly"
      :url         "https://plot.ly/javascript/"
      :scm         {:url "https://github.com/plotly/plotly.js"}
      :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
   (download  :url      (format "https://github.com/plotly/plotly.js/archive/v%s.zip" +lib-version+)
              :checksum "B29E4A288CA67BA1C311998C7064D650"
              :unzip    true)
   (sift      :move     {#"^plotly(.*)/dist/plotly.js"
                         "cljsjs/plotly/development/plotly.inc.js"
                         #"^plotly(.*)/dist/plotly.min.js"
                         "cljsjs/plotly/production/plotly.min.inc.js"})
   (sift      :include  #{#"^cljsjs"})
   (deps-cljs :name     "cljsjs.plotly")
   (pom)
   (jar)))
