# Push to GitHub — One-time Setup

## Step 1: Create repo on GitHub
Go to https://github.com/new and create:
- Repository name: `quantity-measurement-app`
- Description: `BridgeLabz — 22-UC Quantity Measurement App (Java → Spring Boot → React → Microservices)`
- Visibility: Public or Private
- DO NOT initialize with README (we already have one)

## Step 2: Push all branches from this folder
Run these commands in PowerShell from this directory:

```powershell
git remote add origin https://github.com/<YOUR_USERNAME>/quantity-measurement-app.git
git push -u origin main
git push -u origin dev
git push -u origin feature/uc01-feet-measurement-equality
git push -u origin feature/uc02-feet-and-inches-equality
```

## Branch strategy reminder
| Branch | Purpose |
|--------|---------|
| `main` | Stable, production-ready |
| `dev` | Integration — all UCs merge here |
| `feature/ucXX-*` | Individual UC development |
