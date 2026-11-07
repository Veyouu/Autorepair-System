# Auto Repair System

## Git & GitHub Setup Guide

Follow this guide to initialize the project repository and upload your code to GitHub.

## 1. First-Time Setup

Run these commands only once when setting up the repository for the first time.

```bash
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/Veyouu/Autorepair-System.git
git push -u origin main
```

### What these commands do:
1. `git init` – Initializes a new Git repository.
2. `git add .` – Adds all project files to staging.
3. `git commit -m "Initial commit"` – Creates the initial commit.
4. `git branch -M main` – Renames the current branch to `main`.
5. `git remote add origin ...` – Connects your local repository to GitHub.
6. `git push -u origin main` – Uploads your project to GitHub.

## 2. Every Time You Make Changes

Whenever you modify, add, or delete files in the project, run:

```bash
git status
git add .
git commit -m "Describe your changes"
git push
```

### What these commands do:
1. `git status` – Checks which files have been modified.
2. `git add .` – Stages all changes.
3. `git commit -m "Describe your changes"` – Saves your changes with a descriptive message.
4. `git push` – Uploads your latest commit to GitHub.

**Example commit messages:**
- `git commit -m "Updated login page"`
- `git commit -m "Fixed database connection"`
- `git commit -m "Added customer management"`
- `git commit -m "Improved dashboard UI"`

## 3. Complete a Step for Each Situation

Follow the appropriate Git procedure depending on your situation.

### Situation 1: First Time Uploading the Project
- Follow **Section 1: First-Time Setup**.
- Make sure your GitHub repository is created.
- Verify that your files appear on GitHub.

### Situation 2: You Made Changes to Existing Files
- Follow **Section 2: Every Time You Make Changes**.
- Use a commit message describing what you changed.

### Situation 3: You Added New Files
- Run `git status` to check the new files.
- Run `git add .` to stage them.
- Commit and push your changes.

### Situation 4: You Deleted Files
- Run `git status` to verify the deleted files.
- Run `git add .` to stage the deletions.
- Commit and push your changes.

### Situation 5: You Want to Check Your Current Git Status
Run:

```bash
git status
```

This shows modified, deleted, untracked, and staged files.

### Situation 6: You Want to View Your Commit History
Run:

```bash
git log --oneline
```

This displays your previous commits.

### Situation 7: You Want to Download the Latest Changes from GitHub
Before making changes, run:

```bash
git pull origin main
```

This updates your local project with the latest version from GitHub.

### Situation 8: You Encounter a Push Rejection
If GitHub has changes that your local repository does not have, run:

```bash
git pull --rebase origin main
git push
```

If conflicts occur, resolve them in the affected files before continuing.

---

## Important Reminders

- Always run `git status` before committing.
- Write meaningful commit messages.
- Always pull the latest changes before working with a shared repository.
- Never run `git init` again if the repository is already initialized.
- Never run `git remote add origin` again if the remote is already configured.
- Do not commit sensitive information such as passwords, API keys, or database credentials.
- Make sure your `.gitignore` file excludes unnecessary files.

## Repository

GitHub: https://github.com/Veyouu/Autorepair-System.git

**Remember:** Commit your changes regularly and push them to GitHub to keep your repository updated.
