const fs = require('fs');
let html = fs.readFileSync('index.html', 'utf8');

// 1. Remove state instagram
html = html.replace("name: '', phone: '', email: '', instagram: '',", "name: '', phone: '', email: '',");

// 2. Remove msg instagram line
html = html.replace("if (formData.instagram) msg += `Instagram: ${formData.instagram}\\n`;", "");

// 3. Replace the flex-x block explicitly
const oldFlexBlock = `<div className="flex space-x-3">
                                                    <div className="flex-1"><label className="text-xs font-bold text-choc-dark block mb-1">Celular (WhatsApp) *</label><input type="tel" maxLength={11} value={formData.phone} onChange={e => setFormData({ ...formData, phone: e.target.value.replace(/\\D/g, '') })} className="w-full border border-[#F0EBE1] rounded-lg p-3 text-sm bg-beige-bg focus:ring-1 focus:ring-gold-soft outline-none" placeholder="Ex: 31991605336" /></div>
                                                    <div className="flex-1"><label className="text-xs font-bold text-choc-dark block mb-1">Instagram</label><input type="text" value={formData.instagram} onChange={e => setFormData({ ...formData, instagram: e.target.value })} className="w-full border border-[#F0EBE1] rounded-lg p-3 text-sm bg-beige-bg focus:ring-1 focus:ring-gold-soft outline-none" placeholder="@seu.perfil" /></div>
                                                </div>`;

const newFlexBlock = `<div className="flex space-x-3">
                                                    <div className="flex-1"><label className="text-xs font-bold text-choc-dark block mb-1">WhatsApp *</label><input type="tel" maxLength={15} value={formData.phone} onChange={e => { let val = e.target.value.replace(/\\D/g, ''); if(val.length > 11) val = val.substring(0, 11); val = val.replace(/^(\\d{2})(\\d)/g, '($1) $2'); val = val.replace(/(\\d)(\\d{4})$/, '$1-$2'); setFormData({ ...formData, phone: val }); }} className="w-full border border-[#F0EBE1] rounded-lg p-3 text-sm bg-beige-bg focus:ring-1 focus:ring-gold-soft outline-none" placeholder="(31) 99160-5336" /></div>
                                                    <div className="flex-1">
                                                        <label className="text-xs font-bold text-choc-dark block mb-1">Cupom de Desconto</label>
                                                        <div className="flex items-center space-x-2">
                                                            <input type="text" maxLength={20} placeholder="PASCOA10" value={promoInput} onChange={(e)=>setPromoInput(e.target.value)} className="w-full border border-[#F0EBE1] rounded-lg p-3 text-sm bg-beige-bg uppercase focus:ring-1 focus:ring-gold-soft outline-none" />
                                                            <button type="button" onClick={applyPromo} className="bg-choc-dark text-white text-xs px-3 py-3 rounded-lg font-bold hover:bg-[#38271B] transition-colors">OK</button>
                                                        </div>
                                                    </div>
                                                </div>`;
html = html.replace(oldFlexBlock, newFlexBlock);

// 4. Wipe out the old Cupom insert block perfectly
const oldCupomBlockRegex = /<div className="pt-2">\s*<label className="text-xs font-bold text-choc-dark block mb-2">Cupom de Desconto<\/label>[\s\S]*?<\/div>\s*<\/div>/;
html = html.replace(oldCupomBlockRegex, '');

fs.writeFileSync('index.html', html);
console.log("Safe replacement complete.");
