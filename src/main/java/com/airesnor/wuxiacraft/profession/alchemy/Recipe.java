package com.airesnor.wuxiacraft.profession.alchemy;

import com.airesnor.wuxiacraft.items.ItemHerb;
import com.airesnor.wuxiacraft.utils.MathUtils;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Recipe {

	private final List<Triple<Float, Float, Item>> recipeOrder;
	private final int[] elements;
	private final float cookTemperatureMin;
	private final float cookTemperatureMax;
	private final int cookTime;

	private final String name;

	private final Item output;
	private final int yields;
	private final int yieldRange;

	public Recipe(String name, float tempMin, float tempMax, int cookTime, Item output, int yields, int yieldRange) {
		this.recipeOrder = new ArrayList<>();
		this.elements = new int[] { 0, 0, 0, 0, 0 };
		this.name = name;
		this.cookTemperatureMin = tempMin;
		this.cookTemperatureMax = tempMax;
		this.cookTime = cookTime;
		this.output = output;
		this.yields = yields;
		this.yieldRange = yieldRange;

		Recipes.RECIPES.add(this);
	}

	public Recipe addRecipeItem(float minTemperature, float maxTemperature, Item item) {
		this.recipeOrder.add(Triple.of(minTemperature, maxTemperature, item));
		return this;
	}

	public Recipe setElements(int water, int wood, int fire, int earth, int metal) {
		elements[0] = water;
		elements[1] = wood;
		elements[2] = fire;
		elements[3] = earth;
		elements[4] = metal;
		return this;
	}

	public boolean checkRecipe(List<Pair<Float, Item>> herbInputs, List<Pair<Float, Item>> nonHerbInputs, int[] totalInputElements) {
		boolean isThis = false;
		// Does the checking against the elements of the recipe
		for (int i = 0; i < totalInputElements.length; i++) {
			if (totalInputElements[i] == elements[i]) {
				isThis = true;
			} else {
				isThis = false;
				break;
			}
		}
		// Does the checking for the temperature of the herbs
		if (isThis) {
			for (int i = 0; i < herbInputs.size(); i++) {
				Pair<Float, Item> input = herbInputs.get(i);
				ItemHerb herb = (ItemHerb) input.getRight();
				if (herb.getMinTemperature() > input.getLeft() || herb.getMaxTemperature() < input.getLeft()) {
					isThis = false;
					break;
				}
			}
		}
		// Does the checking for the temperature of the non-herb items and whether they are the correct non-herb items
		if (isThis) {
			for (int i = 0; i < nonHerbInputs.size(); i++) {
				if (nonHerbInputs.size() <= recipeOrder.size()) {
					Triple<Float, Float, Item> element = recipeOrder.get(i);
					Pair<Float, Item> input = nonHerbInputs.get(i);
					if (input.getRight() == element.getRight()) {
						isThis = MathUtils.between(input.getLeft(), element.getLeft(), element.getMiddle());
						if (!isThis) break;
					} else {
						isThis = false;
						break;
					}
				} else {
					isThis = false;
					break;
				}
			}
			if (nonHerbInputs.size() == 0 && recipeOrder.size() != 0) {
				isThis = false;
			}
		}
		return isThis;
	}

	public float getCookTemperatureMin() {
		return cookTemperatureMin;
	}

	public float getCookTemperatureMax() {
		return cookTemperatureMax;
	}

	public int getCookTime() {
		return cookTime;
	}

	public String getName() {
		ITextComponent iTextComponent = new TextComponentString("");
		iTextComponent.appendSibling(new TextComponentTranslation("wuxiacraft.recipes.name." + this.getUnlocalizedName()));
		return iTextComponent.getFormattedText();
	}

	public String getUnlocalizedName() {
		return name;
	}

	public int[] getElements() {
		return elements;
	}

	public Item getCatalyst() {
		return recipeOrder.get(recipeOrder.size() - 1).getRight();
	}

	public Item getOutput() {
		return this.output;
	}

	public int getRecipeItemCount() {
		return this.recipeOrder.size();
	}

	public int getYieldResult() {
		Random random = new Random();
		int range = 0;
		if(this.yieldRange > 0) {
			range = random.nextInt(this.yieldRange * 2) - this.yieldRange;
		}
		return this.yields + range;
	}

	public List<Pair<Float, Item>> getRecipe() {
		List<Pair<Float, Item>> recipe = new ArrayList<>();
		for(Triple<Float, Float, Item> item : this.recipeOrder) {
			recipe.add(Pair.of((item.getLeft() + item.getMiddle())/2f, item.getRight()));
		}
		return recipe;
	}
}