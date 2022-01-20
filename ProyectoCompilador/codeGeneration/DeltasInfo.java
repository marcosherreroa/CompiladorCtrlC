package codeGeneration;

public class DeltasInfo {
    private int c;
    private int max;
    
    public DeltasInfo() {
    	this.c = 0;
    	this.max = 0;
    }
    
    public DeltasInfo(DeltasInfo DI) {
    	this.c = DI.c;
    	this.max = DI.max;
    }

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
    
    
}
