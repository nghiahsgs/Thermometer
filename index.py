class Threshold:
    def __init__(self, value, observer, direction_matters=False, only_when_decreasing=False, tolerance=0):
        self.value = value
        self.observer = observer
        self.direction_matters = direction_matters
        self.only_when_decreasing = only_when_decreasing
        self.tolerance = tolerance
        
    def is_reached(self, old_temperature, new_temperature):
        if not self.direction_matters:
            old_diff = old_temperature - self.value
            if abs(old_diff) <= self.tolerance:
                old_diff=0
            
            new_diff = new_temperature - self.value
            if abs(new_diff) <= self.tolerance:
                new_diff=0
            
            print(f'old_diff: {old_diff}, new_diff: {new_diff}')
            if old_diff * new_diff <= 0:
                return True
        else:
            old_diff = old_temperature - self.value
            if abs(old_diff) <= self.tolerance:
                old_diff=0
            
            new_diff = new_temperature - self.value
            if abs(new_diff) <= self.tolerance:
                new_diff=0
            
            if old_diff * new_diff <= 0:
                if self.only_when_decreasing:
                    if old_diff > 0 and new_diff <= 0:
                        return True
                else:
                    if old_diff < 0 and new_diff >= 0:
                        return True
        return False
            

    def notify_observer(self):
        self.observer(self.value)

class Thermometer:
    def __init__(self):
        self.temperature = 0
        self.thresholds = []

    def set_temperature(self, celsius):
        old_temperature = self.temperature
        self.temperature = celsius
        self._check_thresholds(old_temperature, self.temperature)

    def get_temperature_celsius(self):
        return self.temperature

    def get_temperature_fahrenheit(self):
        return (self.temperature * 9/5) + 32

    def add_threshold(self, threshold):
        self.thresholds.append(threshold)

    def _check_thresholds(self, old_temperature, new_temperature):
        for threshold in self.thresholds:
            if threshold.is_reached(old_temperature, new_temperature):
                threshold.notify_observer()

# Hàm callback ví dụ
def alert(temp):
    print(f"Cảnh báo: Nhiệt độ đã đạt ngưỡng {temp}°C!")

# Sử dụng ví dụ
if __name__ == "__main__":
    thermometer = Thermometer()
    
    # Thêm ngưỡng cho điểm đóng băng (0°C) với dung sai 0.5°C
    freezing_threshold = Threshold(0, alert, direction_matters=True, only_when_decreasing=True, tolerance=0.5)
    thermometer.add_threshold(freezing_threshold)
    
    # Thêm ngưỡng cho điểm sôi (100°C) không quan tâm hướng
    boiling_threshold = Threshold(100, alert, tolerance=1)
    thermometer.add_threshold(boiling_threshold)

    # Mô phỏng thay đổi nhiệt độ
    temperatures = [25, 10, 5, 1, 0.2, -0.3, 0, 0.5, 1, 50, 99, 100.5, 101, 100, 99,98,97]
    for temp in temperatures:
        print(f"\nNhiệt độ hiện tại: {temp}°C ({thermometer.get_temperature_fahrenheit():.2f}°F)")
        thermometer.set_temperature(temp)