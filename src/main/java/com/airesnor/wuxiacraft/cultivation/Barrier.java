package com.airesnor.wuxiacraft.cultivation;

public class Barrier implements IBarrier {

    private float barrierAmount;
    private float barrierMaxAmount;
    private float barrierRegenRate;
    private int barrierCooldown;
    private int barrierMaxCooldown;
    private int barrierHits;

    private boolean barrierRegenActive;
    private boolean barrierActive;
    private boolean barrierBroken;

    public Barrier() {
        this.barrierAmount = 0;
        this.barrierMaxAmount = 0;
        this.barrierRegenRate = 0;
        this.barrierCooldown = 0;
        this.barrierMaxCooldown = 3600;
        this.barrierRegenActive = true;
        this.barrierActive = false;
        this.barrierBroken = false;
        this.barrierHits = 0;
    }

    @Override
    public void setBarrierAmount(float amount) {
        this.barrierAmount = Math.max(amount, 0.0f);
    }

    @Override
    public void addBarrierAmount(float amount) {
        this.barrierAmount += Math.max(0, amount);
    }

    @Override
    public void removeBarrierAmount(float amount) {
        this.barrierAmount -= Math.max(0, amount);
    }

    @Override
    public float getBarrierAmount() {
        return this.barrierAmount;
    }

    @Override
    public void setBarrierBroken(boolean barrierBroken) {
        this.barrierBroken = barrierBroken;
    }

    @Override
    public boolean isBarrierBroken() {
        return this.barrierBroken;
    }

    @Override
    public void setBarrierHits(int hits) {
        this.barrierHits = Math.max(0, hits);
    }

    @Override
    public void stepBarrierHits() {
        this.barrierHits++;
    }

    @Override
    public int getBarrierHits() {
        return this.barrierHits;
    }

    @Override
    public void setBarrierMaxAmount(float amount) {
        this.barrierMaxAmount = Math.max(0, amount);
    }

    @Override
    public float getBarrierMaxAmount() {
        return this.barrierMaxAmount;
    }

    @Override
    public void setBarrierRegenRate(float regenRate) {
        this.barrierRegenRate = regenRate;
    }

    @Override
    public float getBarrierRegenRate() {
        return this.barrierRegenRate;
    }

    @Override
    public void setBarrierRegenActive(boolean regenActive) {
        this.barrierRegenActive = regenActive;
    }

    @Override
    public boolean isBarrierRegenActive() {
        return this.barrierRegenActive;
    }

    @Override
    public void setBarrierCooldown(int cooldown) {
        this.barrierCooldown = Math.max(0, cooldown);
    }

    @Override
    public void addBarrierCooldown(int cooldown) {
        this.barrierCooldown += Math.max(0, cooldown);
    }

    @Override
    public void removeBarrierCooldown(int cooldown) {
        this.barrierCooldown -= Math.max(0, cooldown);
    }

    @Override
    public int getBarrierCooldown() {
        return this.barrierCooldown;
    }

    @Override
    public void setBarrierMaxCooldown(int maxCooldown) {
        this.barrierMaxCooldown = Math.max(0, maxCooldown);
    }

    @Override
    public int getBarrierMaxCooldown() {
        return this.barrierMaxCooldown;
    }

    @Override
    public void setBarrierActive(boolean active) {
        this.barrierActive = active;
    }

    @Override
    public boolean isBarrierActive() {
        return this.barrierActive;
    }

    @Override
    public void copyFrom(IBarrier barrier) {
        this.barrierAmount = barrier.getBarrierAmount();
        this.barrierMaxAmount = barrier.getBarrierMaxAmount();
        this.barrierRegenRate = barrier.getBarrierRegenRate();
        this.barrierCooldown = barrier.getBarrierCooldown();
        this.barrierMaxCooldown = barrier.getBarrierMaxCooldown();
        this.barrierRegenActive = barrier.isBarrierRegenActive();
        this.barrierActive = barrier.isBarrierActive();
        this.barrierBroken = barrier.isBarrierBroken();
        this.barrierHits = barrier.getBarrierHits();
    }
}
