
# Gauss Elimination endpoint
```bash
curl -X POST http://localhost:8080/direct/gauss-elimination \
  -H "Content-Type: application/json" \
  -d '{"matrix": [[3,2],[1,2]], "vector": [5,3]}' | jq
```



# Gauss Jordan endpoint 
```bash 
curl -X POST http://localhost:8080/direct/gauss-jordan \
  -H "Content-Type: application/json" \
  -d '{"matrix": [[2,1],[5,7]], "vector": [11,13]}' | jq
```


# Jacobi endpoint
```bash
curl -X POST http://localhost:8080/iterative/jacobi \
  -H "Content-Type: application/json" \
  -d '{"matrix": [[10,2],[1,5]], "vector": [7,6], "itrConfig": {"tol": 1e-6, "maxItrs": 25, "steps": true}}' | jq
```
```
```



# Gauss-Seidel endpoint
```bash
curl -X POST http://localhost:8080/iterative/seidel \
  -H "Content-Type: application/json" \
  -d '{"matrix": [[10,2],[1,5]], "vector": [7,6], "itrConfig": {"tol": 1e-6, "maxItrs": 25, "steps": true}}' | jq
```


# Bisection endpoint
```bash
curl -X POST http://localhost:8080/root/bisection \
  -H "Content-Type: application/json" \
  -d '{"coeff": [1,0,-2,-5], "lowerB": 1, "upperB": 3, "iterConfig": {"tol": 1e-6, "maxItrs": 25, "steps": true }}' | jq
```

# Falsi endpoint
```bash
curl -X POST http://localhost:8080/root/falsi \
  -H "Content-Type: application/json" \
  -d '{"coeff": [1,0,-2,-5], "lowerB": 1, "upperB": 3, "iterConfig": {"tol": 1e-6, "maxItrs": 25, "steps": true}}' | jq
```

# Rapson endpoint
```bash
curl -X POST http://localhost:8080/root/rapson \
  -H "Content-Type: application/json" \
  -d '{"coeff": [1,0,-2,-5], "initialGuess": 2, "iterConfig": {"tol": 1e-6, "maxItrs": 25, "steps": true }}' | jq
```



# Secant endpoint 
```bash
curl -X POST http://localhost:8080/root/secant \
  -H "Content-Type: application/json" \
  -d '{"coeff": [1,0,-2,-5], "x0": 1, "x1": 3, "iterConfig": {"tol": 1e-6, "maxItrs": 25, "steps": true }}' | jq
```
