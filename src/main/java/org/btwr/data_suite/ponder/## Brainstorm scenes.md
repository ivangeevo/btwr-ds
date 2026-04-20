# Better With Time: Ponder Scene Development Plan (1.21.1 Fabric)

This document outlines the proposed Ponder scenes for the **Better With Time (BWT)** mod, designed to help players navigate mechanical power and complex block interactions using the Create Mod's Ponder UI.

---

## 1. Mechanical Power & Logistics
*The foundation of BWT. These scenes focus on power generation and the risks of mechanical failure.*

### [ ] The Windmill
* **Objective:** Demonstrate large-scale power.
* **Key Visuals:** Vertical/Horizontal placement, clearing obstructions in the sail radius, and using Dye on sails for customization.

### [ ] Gearbox & Axle Logic
* **Objective:** Explain redirection and "Overstress."
* **Key Visuals:** Using a Screwdriver/Wrench to toggle Gearbox faces. A "Warning" sequence showing a Gearbox breaking if two different power sources are connected to it.

### [ ] The Water Wheel
* **Objective:** Show spatial requirements and orientation.
* **Key Visuals:** 5x5 vertical clearance, water flow direction influencing rotation, and connecting the first Axle.

---

## 2. Early Processing: The Millstone
*The first step into automated refinement.*

### [ ] The Millstone Basics
* **Objective:** Input/Output flow.
* **Key Visuals:** Items (Hemp/Wheat) being dropped into the top. Resulting items popping out the sides into a collection stream.

### [ ] Automated Grinding
* **Objective:** Integrating Hoppers.
* **Key Visuals:** A Hopper feeding the top and a **Turntable** rotating a collection chest/hopper below.

---

## 3. The Saw & Woodworking
*Visualizing the dangerous but necessary wood-processing line.*

### [ ] The Cutting Blade
* **Objective:** Show "Siding, Moulding, and Corner" logic.
* **Key Visuals:** A Log being pushed into the Saw to become Planks, and Planks becoming Sidings.

### [ ] The Danger Zone (Safety Warning)
* **Objective:** Remind players that machines are lethal.
* **Key Visuals:** A "Villager" or "Zombie" entity walking into the Saw and being converted into meat/loot.

---

## 4. Advanced Alchemy: The Cauldron & Crucible
*Explaining the "Stoked" fire mechanic which is often confusing for new players.*

### [ ] The Hibachi & Bellows
* **Objective:** Visualizing "Stoked Fire."
* **Key Visuals:** A Hibachi lit with Flint & Steel. Mechanical power hitting a Bellows, causing the fire to turn Blue/Intense.

### [ ] The Filtered Hopper
* **Objective:** Explain the many modes of the Hopper.
* **Key Visuals:** * Placing **Wicker** in the filter slot to make Flour.
    * Placing a **Soul Filter** to catch Soul Dust.
    * The visual "clogging" of the hopper when the wrong item is used.

### [ ] The Crucible & Steel
* **Objective:** High-tier metallurgy.
* **Key Visuals:** Combining Iron and Soul Dust in a Crucible over Stoked Fire to produce Soulforged Steel.

---

## 5. Automation & Sensors
*Niche blocks that replace modern redstone logic.*

### [ ] The Turntable
* **Objective:** Pottery and Timing.
* **Key Visuals:** Placing a Clay Block on top. Showing the 4 speed settings via the UI or a Screwdriver. The block cycling through "Wet Pot" stages.

### [ ] The Buddy Block
* **Objective:** Block Update Detection (BUD).
* **Key Visuals:** A block being placed in front of the Buddy Block, triggering a redstone pulse to a nearby Piston.

### [ ] The Lens
* **Objective:** Long-range detection.
* **Key Visuals:** A beam of light (or particle effect) detecting a Mob passing through it, triggering a redstone signal for a trap.

---

## Technical Implementation Notes
* **1.21.1 Specifics:** Ensure all scenes use the new 1.21 copper/tuff textures where appropriate.
* **Particle Effects:** Use custom flame particles for "Stoked" vs "Unstoked" heat sources.
* **Tooltips:** Ensure every Ponder has "Hold [Shift] for more info" prompts to link to the in-game guidebook.