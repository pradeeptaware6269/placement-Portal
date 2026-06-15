// PlaceTrack – Basic JS Validation

document.addEventListener('DOMContentLoaded', function () {

    // ── CGPA validation ──────────────────────────────────────────────
    const cgpaInput = document.getElementById('cgpa');
    if (cgpaInput) {
        cgpaInput.addEventListener('input', function () {
            const val = parseFloat(this.value);
            if (val < 0 || val > 10) {
                this.setCustomValidity('CGPA must be between 0 and 10');
            } else {
                this.setCustomValidity('');
            }
        });
    }

    // ── Confirm password ─────────────────────────────────────────────
    const pwd = document.getElementById('password');
    const cpwd = document.getElementById('confirmPassword');
    if (pwd && cpwd) {
        cpwd.addEventListener('input', function () {
            if (this.value !== pwd.value) {
                this.setCustomValidity('Passwords do not match');
            } else {
                this.setCustomValidity('');
            }
        });
    }

    // ── Phone validation ─────────────────────────────────────────────
    const phone = document.getElementById('phone');
    if (phone) {
        phone.addEventListener('input', function () {
            const re = /^[6-9]\d{9}$/;
            if (this.value && !re.test(this.value)) {
                this.setCustomValidity('Enter a valid 10-digit Indian mobile number');
            } else {
                this.setCustomValidity('');
            }
        });
    }

    // ── Auto dismiss alerts ───────────────────────────────────────────
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (el) {
        setTimeout(function () {
            el.style.opacity = '0';
            el.style.transition = 'opacity .5s';
            setTimeout(function () { el.remove(); }, 500);
        }, 4000);
    });

    // ── Confirm delete ────────────────────────────────────────────────
    document.querySelectorAll('.btn-confirm-delete').forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            if (!confirm('Are you sure you want to delete this?')) {
                e.preventDefault();
            }
        });
    });
});
