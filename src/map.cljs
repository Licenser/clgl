(ns ds.map)


(def units (atom {}))
(def map (atom {}))
(def coords (atom {}))
(def unit-canvas)
(def fire-canvas)

(def color-for-team {"one" "blue"
                     "two" "red"})

(def animate false)
(def selection (atom {:fixed true
                      :needs-update false
                      :hexagon nil}))

(def attacks (atom []))



(def selected-unit-id (atom nil))

(defn epic-to-evil [x y]
  { :x x :y y })
(defn evil-to-epic [x y]
  { :x x :y y })

(defn or2 [x y] (or x y))

(defn coords [x y]
  (get-in (swap! coords update-in [x y] or2 (str x "/" y)) [x y]))

(defn add-unit [unit]
  (swap! units assoc (:id @unit) unit))

(defn unit-at [x y]
  (get @map {:x x :y y}))

(defn set-unit-at [x y uid]
  (let [unit (get @units uid)
        pos (if unit (:position @unit) {})]
    (if (and unit pos)
      (do
        (swap! map dissoc pos)
        (when animate
          (clear-hexagon (:x pos) (:y pos) 'fine'))))
    (swap! map assoc pos unit)
    (when unit
      (when animate
        (draw-unit-at (:x pos) (:y pos)))
      (when (and
             (= uid @selected-unit-id)
             (:fixed @selection))
        (swap! selection assoc
               :needs-update true
               :hexagon {:col x :row y})
        (swap! unit assoc :position {:x x :y y})))
    ))

(defn destroy-unit [uid]
  (let [unit (get @units uid)]
    (let [{{x :x
            y :y
            :as position} :position
            {hull :hull} :type} @unit]
      (swap! units dissoc uid)
      (set-unit-at x y null)
      (swap! attacks conj {:type :explosion
                           :position position
                           :might hull})
      (if (= id @selected-unit-id)
        
        (do
          (reset! selected-unit-id nil)
                                        ;        DS.selection.selectedHexagon = null;
                                        ;        DS.selection.selectionFixed = null;
                                        ;        DS.selection.needsUpdate = true;
          )))))
        


  initMap: function() {
    this.map = {
      unitsCanvas: this.units,
      fireCanvas: this.fire,

      highlightUnit: function(unitID, color) {
        var unit = this.units[unitID];
        if (unit) {
          switch (color) {  // convert color name to hue value
          case 'red': color = '0'; break;
          case 'orange': color = '30'; break;
          case 'yellow': color = '60'; break;
          case 'green': color = '120'; break;
          case 'cyan': color = '180'; break;
          case 'blue': color = '240'; break;
          case 'magenta': color = '300'; break;
          default: if (console) console.error('invalid color: ' + color); break;
          }
          unit.highlight = color;
          this.unitsCanvas.drawUnitAt(unit.position.x, unit.position.y);
        }
      },
      setInfo: function(unitID, message) {
        var unit = this.units[unitID];
        if (unit) {
          unit.info = message;
        }
      },
      appendInfo: function(unitID, message) {
        var unit = this.units[unitID];
        if (unit) {
          if (!unit.info)
            unit.info = message;
          else if (unit.info instanceof Array)
            unit.info.push(message);
          else if (typeof unit.info === 'string')
            unit.info += message;
          else
            if (console) console.error('cannot append to ' + unit.info);
        }
      },
      addTargetMarker: function(unitID, targetID) {
        this.addAttack(unitID, targetID, 'target');
      },
      addAttack: function(unitID, targetID, damage, impacts) {
        var source = this.units[unitID];
        var target = this.units[targetID];
        this.attacks.push({
          type: 'laser',
          source: { x: source.position.x, y: source.position.y,
            teamColor: this.colorForTeam(source.team) },
          target: { x: target.position.x, y: target.position.y,
            teamColor: this.colorForTeam(target.team) },
          damage: damage,
          impacts: impacts
        });
        if (target && impacts) {
          for (var i in impacts) {
            var impact = impacts[i];
            if (impact.type === 'impact') {
              target.damage += impact.damage;
              this.unitsCanvas.clearHexagon(target.position.x, target.position.y, 'fine');
              this.unitsCanvas.drawUnitAt(target.position.x, target.position.y);
            }
          }
        }
        if (this.animate) this.fireCanvas.needsUpdate = true;
      },
      clearAttacks: function() {
        this.attacks = [];
        if (this.animate) this.fireCanvas.needsUpdate = true;
      }
    };
  },
  