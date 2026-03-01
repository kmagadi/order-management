import requests
import random
import threading

URL = "http://localhost:8080/orders"

customers = ["CUST_001", "CUST_002", "CUST_003", "CUST_004"]
products = ["AAPL", "GOOG", "TSLA", "MSFT"]

def place_order():
    payload = {
        "customerId": random.choice(customers),
        "product": random.choice(products),
        "orderType": random.choice(["BUY", "SELL"]),
        "quantity": random.randint(1, 50),
        "price": random.randint(100, 3000)
    }

    try:
        response = requests.post(URL, json=payload)
        print(response.json())
    except Exception as e:
        print("Error:", e)

threads = []

# send 20 concurrent orders
for _ in range(20):
    t = threading.Thread(target=place_order)
    threads.append(t)
    t.start()

for t in threads:
    t.join()

print("Load test completed.")