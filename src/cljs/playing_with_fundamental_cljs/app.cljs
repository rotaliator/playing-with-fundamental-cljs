(ns playing-with-fundamental-cljs.app
  (:require [reagent.core :as r :refer [atom]]
            ["fundamental-react" :refer [Shellbar Calendar
                                         SideNav SideNavList SideNavListItem
                                         PanelGrid Panel
                                         TileGrid Tile TileContent TileMedia Image Identifier
                                         Table
                                         Popover
                                         Menu MenuList MenuItem
                                         Button
                                         DatePicker]]))

(def api-endpoint "https://reqres.in/api/unknown")

(defonce app-state (r/atom {:table-data []}))


(defn get-data []
  (-> (js/fetch api-endpoint)
      (.then (fn [r] (.json r)))
      (.then (fn [json] (swap! app-state assoc :resp (js->clj json :keywordize-keys true))))))

(defn get-header [r]
  (->> r :data first keys (mapv name)))
(defn get-rows [r]
  (->> r :data (mapv vals) (mapv (partial hash-map :rowData))))

(def profile #js{:initials "JD"
                 :userName "John Doe"
                 :colorAccent 8})

(def profile-menu  [
                    { :name "Settings", :glyph "action-settings", :size "s", :callback #(js/alert "Settings") },
                    { :name "Sign Out", :glyph "log", :size "s", :callback #(js/alert "Sign Out") }])


(def sap-logo (r/create-element "img" #js{:src "//unpkg.com/fiori-fundamentals/dist/images/sap-logo.png" :alt "SAP"}))
(def menu-button (r/create-element Button #js{:option "light" :glyph "vertical-grip"}))
(def popover-menu (r/create-element Menu #js{}
                                    (r/create-element MenuList #js{}
                                                      (r/create-element MenuItem #js{:url "#"} "Option 1")
                                                      (r/create-element MenuItem #js{:url "#"} "Option 2")
                                                      (r/create-element MenuItem #js{:url "#"} "Option 3")
                                                      (r/create-element MenuItem #js{:url "#"} "Option 4"))))

(defn app []
  [:div
   #_[:p (pr-str @app-state)]
   [:> Shellbar {:logo sap-logo
                 :profile profile
                 :profileMenu profile-menu
                 :productTitle "Corporate Portal ;)"}]
   [:> PanelGrid {:cols 6}
    [:> Panel {:colSpan 1}
     [:> SideNav
      [:> SideNavList {:title "Group 1"}
       [:> SideNavListItem {:id "item1"
                            :name "List item 1"
                            :url "#"}]
       [:> SideNavListItem {:id "item2"
                            :name "List item 2"
                            :url "#"}]]
      [:> SideNavList {:title "Group 2"}
       [:> SideNavListItem {:id "item3"
                            :name "List item 3"
                            :url "#"}]]]]
    [:> Panel {:colSpan 5}
     [:> TileGrid {:col 3}
      [:> Tile {:role "button"}
       [:> TileContent {:title "Tile title"} "First tile"]]
      [:> Tile {:role "button"}
       [:> TileContent {:title "Tile title"} "Second tile"]]
      [:> Tile {:role "button"}
       [:> TileMedia
        [:> Image {:size "l" :type "circle" :photo "https://placeimg.com/200/200/nature"}]
        [:> TileContent "Tile with icon"]]]
      [:> Tile {:role "button"}
       [:> TileMedia
        [:> Identifier {:modifier "circle" :size "l" :glyph "add" :color 7}]]
       [:> TileContent {:title "Title"} "Tile with glyph"]]
      [:> Tile {:role "button"}
       [:> TileContent {:title "Title"} "Tile content"]]
      [:> Tile {:role "button"}
       [:> TileMedia
        [:> Image {:size "l" :type "circle" :photo "https://placeimg.com/200/200/tech"}]
        [:> TileContent {:title "Icon"} "Tile with icon"]]]]
     [:> Table {:headers ["Header 1" "Header 2" "Header 3" "Header 4"]
                :tableData [{:rowData ["Value 1" "Value 2" "Value 2"]}
                            {:rowData ["Value 1" "Value 2" "Value 3"
                                       (r/create-element Popover #js{:control menu-button
                                                                     :body popover-menu})]}
                            {:rowData ["Value 1" "Value 2" nil "Value 4"]}]}]
     [:> Button {:option "emphasized"
                 :on-click get-data} "Fetch data"]
     [:> Table {:headers (get-header (:resp @app-state))
                :tableData (get-rows (:resp @app-state))}]
     [:> DatePicker {:disableWeekends true
                     :disableAfterDate (js/Date.)}]]]])


(defn mount-root []
  (r/render [app] (.getElementById js/document "app")))

(defn ^:export main []
(mount-root))
