const functions = require('firebase-functions');
const admin = require('firebase-admin');
const nodemailer = require('nodemailer');

admin.initializeApp();

// Konfigurasi SMTP Gmail
const gmailEmail = functions.config().gmail.email;
const gmailPassword = functions.config().gmail.password;

const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: gmailEmail,
        pass: gmailPassword
    }
});

exports.sendCustomVerificationEmail = functions.auth.user().onCreate((user) => {
    const mailOptions = {
        from: gmailEmail,
        to: user.email,
        subject: 'Verify your email address',
        html: `<p>Hello ${user.displayName || 'User'},</p>
               <p>Thank you for registering. Please verify your email by clicking the link below:</p>
               <a href="https://example.com/verifyEmail/${user.uid}">Verify Email</a>`
    };

    return transporter.sendMail(mailOptions).then(() => {
        console.log('Verification email sent to:', user.email);
    }).catch((error) => {
        console.error('Error sending email:', error);
    });
});
