public class Rabbit {
	
	private final int deathAge;
	
	private int age;
	
	public Rabbit(int deathAge) {
		this.deathAge = deathAge;
	}
	
	public void age() {
		if(!isDead()) age++;
	}
	
	public int getAge() {
		return age;
	}
	
	public boolean isDead() {
		if(deathAge < 1) return false;
		return age >= deathAge;
	}
	
	public boolean isMature() {
		return !isDead() && age > 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(isMature()) {
			sb.append("Зрелая");
		} else {
			if(isDead()) {
				sb.append("Мертвая");
			} else {
				sb.append("Незрелая");
			}
		}
		
		sb.append(" пара кроликов (");
		sb.append(age);
		sb.append(')');
		
		return sb.toString();
	}
}

